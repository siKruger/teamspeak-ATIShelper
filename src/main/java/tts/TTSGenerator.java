package tts;

import com.github.manevolent.ffmpeg4j.*;
import com.github.manevolent.ffmpeg4j.filter.audio.FFmpegAudioResampleFilter;
import com.github.manevolent.ffmpeg4j.source.FFmpegAudioSourceSubstream;
import com.github.manevolent.ffmpeg4j.stream.source.FFmpegSourceStream;
import converter.AudioConverter;
import marytts.LocalMaryInterface;
import marytts.MaryInterface;
import marytts.exceptions.MaryConfigurationException;
import marytts.exceptions.SynthesisException;
import ws.schild.jave.EncoderException;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import java.io.*;
import java.util.Collection;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

import static tts.TeamspeakFastMixerSink.AUDIO_FORMAT;
public class TTSGenerator {

    public static void speakText(String text, TeamspeakFastMixerSink sink) throws IOException, MaryConfigurationException, SynthesisException, EncoderException, FFmpegException {
        File ttsWavFile = new File("src\\main\\resources\\tts.wav");

        generateWavFromString(ttsWavFile, text);
        AudioConverter.wavToMp3(ttsWavFile, new File("src\\main\\resources\\tts.mp3"));
        playLastMp3(sink);
    }

    private static void generateWavFromString(File outputFile, String text) throws MaryConfigurationException, SynthesisException, IOException {
        MaryInterface tts = new LocalMaryInterface();
        tts.setVoice("cmu-bdl-hsmm");
        tts.setAudioEffects("Rate(durScale:1.3)");

        AudioInputStream spoken = tts.generateAudio(text);
        AudioFileFormat.Type spokenFormat = AudioFileFormat.Type.WAVE;
        AudioSystem.write(spoken, spokenFormat, outputFile);
    }


    /*
    Credits:
    https://github.com/Manevolent
    https://github.com/Manevolent/ts3j
    */

    private static void playLastMp3(TeamspeakFastMixerSink sink) throws FFmpegException, IOException {
        FFmpeg.register();
        InputStream inputStream = new FileInputStream("src\\main\\resources\\tts.mp3");


        FFmpegInput input = new FFmpegInput(inputStream);
        FFmpegSourceStream stream = input.open(FFmpeg.getInputFormatByName("mp3"));
        FFmpegAudioSourceSubstream audioSourceSubstream =
                (FFmpegAudioSourceSubstream) stream.registerStreams()
                        .stream()
                        .filter(x -> x.getMediaType() == MediaType.AUDIO)
                        .findFirst().orElse(null);


        int bufferSize = AUDIO_FORMAT.getChannels() * (int) AUDIO_FORMAT.getSampleRate(); // Just to keep it orderly
        FFmpegAudioResampleFilter resampleFilter = new FFmpegAudioResampleFilter(
                Objects.requireNonNull(audioSourceSubstream).getFormat(),
                new com.github.manevolent.ffmpeg4j.AudioFormat(
                        (int) AUDIO_FORMAT.getSampleRate(),
                        AUDIO_FORMAT.getChannels(),
                        FFmpeg.guessFFMpegChannelLayout(AUDIO_FORMAT.getChannels())
                ),
                bufferSize
        );


        Queue<AudioFrame> frameQueue = new LinkedBlockingQueue<>();
        AudioFrame currentFrame = null;
        int frameOffset = 0; // offset within current frame

        long wake = System.nanoTime();
        long delay = 150 * 1_000_000; // 50ms interval
        long sleep;
        long start = System.nanoTime();
        double volume = 0.5D;

        while (true) {
            int available = sink.availableInput();

            if (available > 0) {
                if (currentFrame == null || frameOffset >= currentFrame.getLength()) {
                    if (frameQueue.peek() == null) {
                        try {
                            AudioFrame frame = audioSourceSubstream.next();
                            for (int i = 0; i < frame.getLength(); i++)
                                frame.getSamples()[i] *= volume;

                            Collection<AudioFrame> frameList = resampleFilter.apply(frame);
                            frameQueue.addAll(frameList);
                        } catch (EOFException ex) {
                            // flush currentFrame
                            break;
                        }
                    }

                    if (frameQueue.size() <= 0) continue;

                    currentFrame = frameQueue.remove();
                    frameOffset = 0;
                }

                int write = Math.min(currentFrame.getLength() - frameOffset, available);


                sink.write(
                        currentFrame.getSamples(),
                        frameOffset,
                        write
                );

                frameOffset += write;

                continue;
            }

            wake += delay;
        }
    }
}
