package api;

import com.voicerss.tts.AudioCodec;
import com.voicerss.tts.Languages;
import com.voicerss.tts.VoiceParameters;
import com.voicerss.tts.VoiceProvider;

import java.io.FileOutputStream;

public class VoiceRssTTS {

    public static void generateMp3FromText(String text) throws Exception {
        VoiceProvider tts = new VoiceProvider("36e045f8951a49cd9152098c3594445c");
        VoiceParameters params = new VoiceParameters(text, Languages.English_UnitedStates);

        params.setVoice("John");
        params.setCodec(AudioCodec.MP3);
        params.setFormat(com.voicerss.tts.AudioFormat.Format_44KHZ.AF_44khz_16bit_stereo);
        params.setBase64(false);
        params.setSSML(false);
        params.setRate(-2);

        byte[] voice = tts.speech(params);


        FileOutputStream fos = new FileOutputStream("voice.mp3");
        fos.write(voice, 0, voice.length);
        fos.flush();
        fos.close();
    }
}
