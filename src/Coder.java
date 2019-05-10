import java.io.*;
import java.util.Scanner;
import java.util.zip.CRC32;

public class Coder {
    String ESCAPE_SEQUENCE = "01111110";
    int ESCAPE_NUMBER = 5;

    String getdata() throws FileNotFoundException {
        String data;
        data = "";
        File file = new File("C:\\Users\\Mareczek\\Desktop\\test.txt");

        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()){
            data=data+sc.nextLine();
        }
        return data;
    }
    void encode() throws IOException {
        String data = getdata();
        CRC32 crc = new CRC32();

        byte test[] = data.getBytes();
        crc.update(test,0,test.length);
        long xd = crc.getValue();
        String crccode = Long.toBinaryString(xd);
        System.out.println( crccode );
        String codeddata = ESCAPE_SEQUENCE;
        data=data+crccode;
        int overflow = 0;
        for(char c : data.toCharArray()){
            codeddata = codeddata + c;
            if(c=='1'){
                overflow++;
            }
            if(c=='0'){
                overflow = 0;
            }
            if(overflow==5){
                codeddata= codeddata + '0';
                overflow = 0;
            }
        }
        codeddata = codeddata + ESCAPE_SEQUENCE;

        BufferedWriter writer = new BufferedWriter(new FileWriter("out.txt"));
        writer.write(codeddata);

        writer.close();



    }
    void decode() throws IOException {
        CRC32 crc32 = new CRC32();

        String data = "";
        File file = new File("out.txt");
        Scanner sc = new Scanner(file);

        while(sc.hasNextLine()){
            data=data+sc.nextLine();
        }
        String[] parts = data.split(ESCAPE_SEQUENCE);
        System.out.println(parts[1]);

        char[] chars = parts[1].toCharArray();
        int overflow = 0;
        String curdata="";
        for(int i = 0; i<chars.length; i++){
            if(chars[i]=='1'){
                overflow++;
                curdata = curdata + chars[i];
            }
            if(chars[i] == '0'){
                overflow=0;
                curdata = curdata + chars[i];
            }
            if(overflow==5){
                i++;
                overflow=0;
            }

        }
        long crc = Long.parseLong(curdata.substring(curdata.length() - 32), 2);
        curdata = curdata.substring(0, curdata.length()-32);
        crc32.reset();
        byte test[] = curdata.getBytes();
        crc32.update(test,0,test.length);
        long xd = crc32.getValue();
        String crccode = Long.toBinaryString(xd);

        BufferedWriter writer = new BufferedWriter(new FileWriter("decoded.txt"));
       if(xd == crc){
           writer.write(curdata);
       }
       else {
       }
        writer.close();




    }
}
