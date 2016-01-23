package scan;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

public class scan {
	private String website1="www.cnn.com";
	private String website2="www.foxnews.com";
	private String website3="www.espn.com";
	private String website4="www.tmz.com";
	private String website5="www.bbc.com/news";
	private String park1 = "http://www.cool18.com/bbs4/index.php?app=forum&act=gold&p=1";
	private String park2= "http://www.cool18.com/bbs4/classbk/md1.shtml";
	
	static public int currentcount =0;
	static public String currentcharset ="";
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		//String results =gethtml("http://www.cool18.com/bbs4/index.php?app=forum&act=gold&p=2");
		//ArrayList<String>  container=findlink1(results);
		
		//container.clone();
		 String workingDir = System.getProperty("user.dir");
		   System.out.println("Current working directory : " + workingDir);
		   //http://www.cool18.com/bbs4/index.php?app=forum&act=threadview&tid=13950945
		   
		//for for first one 
		   for(int i = 13950945;i< 13950947;i++){
			   scan1(i);
		   }
	}
    //scan first link
	static String scan1(int input) throws IOException{
		String init = "http://www.cool18.com/bbs4/index.php?app=forum&act=threadview&tid="+Integer.toString(input);
		
		final URL url = new URL(init);
    	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
    	huc.setRequestMethod("HEAD");
    	if(huc.getResponseCode()== 200){
    		String temp = sethtml(init);
    		System.out.println(new String(temp.substring(680, 800).getBytes("GBK"),"EUC_CN")); 
    		if(temp.contains("bodybegin")){
    			C_Page(temp,currentcount +1);
    		}
    		return null;
    	}
    		else return null;
	}
	
	//scan first link
		String scan2(String input){
			
			
			return null;
		}
		
	//construct the webpage
	static void C_Page(String input,int count) throws IOException{
		
		// InputStreamReader inStrReader = new InputStreamReader(input, Charset.forName("GB2312"));

		String start = "<html>";
		String end="</html>";
		String headstart="<head>";
		String headend="</head>";
		String bodystart="<body>";
		String bodyend="</body>";
		//String charset="<meta http-equiv=\"Content-Type\" content=\"text/html; charset=GB2312\">";
		String charset2="<meta http-equiv=\"Content-Type\" content=\"text/html;charset="+currentcharset+"\">";
		String titlestart ="<title>";
		String titleend ="</title>";
		String script = "<script type=\"text/javascript\" src=\"http://banners.adultfriendfinder.com/go/page/js_im_box_v3?plain_text=1&skip_lpo=1&delay=5&ad=008&size=250x250&audio=1&logo_glow=5&page=search&pid=g761692-pct\" charset=\"UTF-8\"></script>";
		
		int beginindex1 = input.indexOf("<title>")+7;
		int endindex1 = input.indexOf("</title>");
		String s1=input.substring(beginindex1, endindex1);
		String tmp3=s1.replaceAll("6park", "9fun9");
		String tmp1=tmp3.replaceAll("留园网", "9fun9");
		
		int beginindex2 = input.indexOf("<!--bodybegin-->");
		int endindex2 = input.indexOf("<!--bodyend-->")+14;
		String s2=input.substring(beginindex2, endindex2);
		String tmp2=s2.replaceAll("6park", "9fun9");
		
		String finalstring= start +  headstart+  charset2 +titlestart+tmp1+ titleend+headend+ bodystart+tmp2 +script+bodyend+ end;
		String workingDir = System.getProperty("user.dir");
		File newHtmlFile = new File(workingDir+"/test/"+count+".html");
		FileUtils.writeStringToFile(newHtmlFile, finalstring, currentcharset);//.writeStringToFile(newHtmlFile, finalstring);
		/*FileWriter fWriter = null;
		BufferedWriter writer = null;
		try {
		    fWriter = new FileWriter(workingDir+"/test/"+count+".html");
		    writer = new BufferedWriter(fWriter);
		    writer.wri.write(finalstring);
		    writer.newLine(); //this is not actually needed for html files - can make your code more readable though 
		    writer.close(); //make sure you close the writer object 
		} catch (Exception e) {
		  //catch any exceptions here
		}*/
		currentcount ++;
		return;
	}
	
	static String gethtml(String input) throws IOException{
		
		// URL url = new URL("http://www.6park.com/");
		URL url = new URL(input);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "iso-8859-1": encoding;
		//String body = IOUtils.toString(in, encoding);Unicode
		String body = IOUtils.toString(in, currentcharset);
		//System.out.println(body);
		
		return body;
	}
	
	static String sethtml(String input) throws UnsupportedEncodingException, IOException{
		
		URL url = new URL(input);
		URLConnection con = url.openConnection();
		Pattern p = Pattern.compile("text/html;\\s+charset=([^\\s]+)\\s*");
		Matcher m = p.matcher(con.getContentType());
		/* If Content-Type doesn't match this pre-conception, choose default and 
		 * hope for the best. */
		String charset = m.matches() ? m.group(1) : "GB2312";
		currentcharset =charset;
		Reader r = new InputStreamReader(con.getInputStream(), charset);
		StringBuilder buf = new StringBuilder();
		while (true) {
		  int ch = r.read();
		  if (ch < 0)
		    break;
		  buf.append((char) ch);
		}
		String str = buf.toString();
		return str;
	}
     static String gethtml1(String input) throws IOException{
		
		// URL url = new URL("http://www.6park.com/");
    	 URL url = new URL(input); 
    	 InputStream in = url.openStream();  
         
         int n;  
         StringBuffer buffer = new StringBuffer();  
           
         // 用字节流读取  
         while((n= in.read()) != -1){  
             buffer.append((char)n);  
         }  
         URLConnection con = url.openConnection();
         String encoding = con.getContentEncoding();
         encoding = encoding == null ? "gb2312": encoding;
      // 转码并打印结果  
         String response = new String(buffer.toString().getBytes(encoding) , "gb2312");  
         System.out.println(response);  
         
	/*	URL url = new URL(input);
		URLConnection con = url.openConnection();
		InputStream in = con.getInputStream();
		String encoding = con.getContentEncoding();
		encoding = encoding == null ? "EUC_CN": encoding;
		//String body = IOUtils.toString(in, encoding);Unicode
		String body = IOUtils.toString(in, "GB2312");
		//System.out.println(body);
*/		
		return response;
	}
	
	static ArrayList<String> findlink1(String input) throws UnknownHostException, IOException{
		String init1="http://www.cool18.com/bbs4/index.php?app=forum&act=threadview&tid=";
		//String init2="http://www.cool18.com/bbs4/classbk/messages/";
		ArrayList<String>  container= new ArrayList<String>();
		
		int lastIndex = 0;
		int count = 0;

		while(lastIndex != -1){

		    lastIndex = input.indexOf("tid=",lastIndex);

		    if(lastIndex != -1){
		    	String tmp1= input.substring(lastIndex+4, lastIndex+20);
		    	int stop = tmp1.indexOf("\"");
		    	String tmp2=tmp1.substring(0, 8);
		    	final URL url = new URL(init1+tmp2);
		    	HttpURLConnection huc = (HttpURLConnection) url.openConnection();
		    	huc.setRequestMethod("HEAD");
		    	if(huc.getResponseCode()== 200)

		    		container.add(init1+tmp2);
		        count ++;
		        lastIndex += "tid=".length();
		    }
		}
		
		//boolean reachable = InetAddress.getByName(init1).isReachable(50);
		
		
		return container;
	}
	
	
	private static String downloadHtml(String urlString) {
	    URL url = null;
	    InputStream inStr = null;
	    StringBuffer buffer = new StringBuffer();

	    try {
	        url = new URL(urlString);
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection(); // Cast shouldn't fail
	        HttpURLConnection.setFollowRedirects(true);
	        // allow both GZip and Deflate (ZLib) encodings
	        //conn.setRequestProperty("Accept-Encoding", "gzip, deflate"); 
	        String encoding = conn.getContentEncoding();
	        inStr = null;

	        // create the appropriate stream wrapper based on
	        // the encoding type
	        if (encoding != null && encoding.equalsIgnoreCase("gzip")) {
	            inStr = new GZIPInputStream(conn.getInputStream());
	        } else if (encoding != null && encoding.equalsIgnoreCase("deflate")) {
	            inStr = new InflaterInputStream(conn.getInputStream(),
	              new Inflater(true));
	        } else {
	            inStr = conn.getInputStream();
	        }
	        int ptr = 0;


	        InputStreamReader inStrReader = new InputStreamReader(inStr, Charset.forName("GB2312"));

	        while ((ptr = inStrReader.read()) != -1) {
	            buffer.append((char)ptr);
	        }
	        inStrReader.close();

	        conn.disconnect();
	    }
	    catch(Exception e) {

	        e.printStackTrace();
	    }
	    finally {
	        if (inStr != null)
	            try {
	                inStr.close();
	            } catch (IOException e) {
	                // TODO Auto-generated catch block
	                e.printStackTrace();
	            }
	    }

	    return buffer.toString();
	  }

	}

