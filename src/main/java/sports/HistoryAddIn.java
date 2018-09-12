package sports;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public class HistoryAddIn {

    public static String sendGet() {

        String spec = "http://www.lottery.gov.cn/historykj/history.jspx?_ltype=dlt";

        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;
        BufferedReader bufferedReader = null;
        String result = null;// 返回结果字符串

        try {
            // 创建远程url连接对象
            URL url = new URL(spec);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            httpURLConnection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            httpURLConnection.setConnectTimeout(3000);
            // 设置读取远程返回的数据时间：60000毫秒
            httpURLConnection.setReadTimeout(6000);
            // 发送请求
            httpURLConnection.connect();
            // 通过connection连接，获取输入流
            inputStream = httpURLConnection.getInputStream();
            // 封装输入流inputStream，并指定字符集
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            // 存放数据
            StringBuffer stringBuffer = new StringBuffer();
            String temp = null;
            while ((temp = bufferedReader.readLine()) != null) {
                stringBuffer.append(temp);
                stringBuffer.append("\r\n");
            }
            result = stringBuffer.toString();

        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != bufferedReader) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            httpURLConnection.disconnect();
        }
        return result;
    }

    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        String response = sendGet();
		System.out.println(response);

//		Pattern pet = Pattern.compile("<tbody>(.|\\n)*</tbody>");
//		Pattern pet = Pattern.compile("\\b\\w{3}\\b");
        Pattern pet = Pattern.compile("<tbody>(.*\\|\\n*)*</tbody>");
        Matcher match = pet.matcher(response);

        System.out.println(match.find());

        System.out.println(match.group(0));
        System.out.println(match.group(1));


        // StringReader sr = new StringReader(response);
        // InputSource is = new InputSource(sr);
        // DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        // DocumentBuilder builder = factory.newDocumentBuilder();
        // Document doc = builder.parse(is);
        //
        // String nodeValue = doc.getNodeValue();


    }

}
