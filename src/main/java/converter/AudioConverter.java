package converter;

import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;

import java.io.File;

public class AudioConverter {


    public static void wavToMp3(File wavFile, File mp3File) throws EncoderException {
        AudioAttributes audioAttributes = new AudioAttributes();

        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setOutputFormat("mp3");
        encodingAttributes.setAudioAttributes(audioAttributes);

        Encoder encoder = new Encoder();
        encoder.encode(new MultimediaObject(wavFile), mp3File, encodingAttributes);
    }
}
