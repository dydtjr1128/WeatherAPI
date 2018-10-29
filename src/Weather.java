import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;

public class Weather {
	public static void main(String[] args) throws IOException {
		Date d = new Date();
		String today;
		if(d.getMonth()+1 >=10)
			today = Integer.toString((d.getYear() + 1900)) +  Integer.toString((d.getMonth()+1)) +  Integer.toString(d.getDate());		
		else
			today = Integer.toString((d.getYear() + 1900)) + "0" + Integer.toString((d.getMonth()+1)) +  Integer.toString(d.getDate());
		StringBuilder urlBuilder = new StringBuilder("http://newsky2.kma.go.kr/service/SecndSrtpdFrcstInfoService2/ForecastSpaceData"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=w%2B4ukdQJ8ag2IQjSkt6BXym4LtZVA9va5FQwc%2FdTuG%2F7g4GeIxv0A3wR0SGwPn9t0wk3wSN8EGYyo%2FMTff7%2F%2FA%3D%3D"); /*Service Key*/        
        urlBuilder.append("&" + URLEncoder.encode("base_date","UTF-8") + "=" + URLEncoder.encode(today, "UTF-8")); /*��15�� 12�� 1�Ϲ�ǥ*/
        System.out.println(today);
        String time;
        int index = 4;
        String[] baset = {"0200", "0500", "0800", "1100", "1400", "1700", "2000", "2300"};//0~7 
        int [] basett ={200, 500, 800, 1100, 1400, 1700, 2000, 2300};//0~7}
        
        if(d.getHours()>=10){
        	time = d.getHours()+"00";
        }
        else{
        	time = "0" + d.getHours() + "00";
        }       
        
        int now = Integer.parseInt(time);
        int tindex=-1;
        while(true){        	
        	
        	if(now<0){
        		now=2400;
        	}
        	for(int i=0; i<basett.length; i++){        		
        		if(now == basett[i]){
        			tindex=i;
        			break;
        		}        			
        	}
        	if(tindex != -1)
        		break;
        	now-=100;        	
        }
        
        if(d.getMinutes()<10)
        	tindex--;
      //  System.out.println(baset[tindex]);
        urlBuilder.append("&" + URLEncoder.encode("base_time","UTF-8") + "=" + URLEncoder.encode(baset[tindex], "UTF-8")); /*05�� ��ǥ * �ϴ� �����ڷ� ����*/
        urlBuilder.append("&" + URLEncoder.encode("category","UTF-8") + "=" + URLEncoder.encode("T3H", "UTF-8")); /*05�� ��ǥ * �ϴ� �����ڷ� ����*/
        urlBuilder.append("&" + URLEncoder.encode("nx","UTF-8") + "=" + URLEncoder.encode("61", "UTF-8")); /*���������� X ��ǥ��*/
        urlBuilder.append("&" + URLEncoder.encode("ny","UTF-8") + "=" + URLEncoder.encode("128", "UTF-8")); /*���������� Y ��ǥ��*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("10", "UTF-8")); /*�� ������ ��� ��*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo","UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*������ ��ȣ*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("xml", "UTF-8")); /*xml(�⺻��), json*/
        URL url = new URL(urlBuilder.toString());
        System.out.println(url.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        //System.out.println("Response code: " + conn.getResponseCode());
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
        //System.out.println(sb.toString());
        String ww[] = {"POP","UUU","REH","TMN","TMX","T3H"};//����Ȯ��(%), ǳ��(M/S),����(%),��ħ����, ��ħ�ְ�,3�ð��µ�
        String www[] = {"����Ȯ�� : ","ǳ�� : ", "���� : ", "��ħ���� : ", "��ħ�ְ� : ", "3�ð���տµ� : "};
        String wwww[] = {"%","m/s", "%", "��C", "��C", "��C"};
        System.out.println("�����ð� : \t\t" + baset[tindex] + "��");
        System.out.println("������� : \t\t" + "�����");
        for(int i=0; i< ww.length; i++){
	        int wsdn = sb.indexOf(ww[i]);
	        
	        wsdn = sb.indexOf("<fcstValue>", wsdn)+11;
	        
	        System.out.printf("%s   \t", www[i]);
	        System.out.println(sb.substring(wsdn,sb.indexOf("<", wsdn)) + wwww[i]);
        }
      //  System.out.println(sb.toString());		
	}

}
