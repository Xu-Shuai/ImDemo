import java.util.HashMap;
import java.util.Map;

public class utils {
    public  static String mapToStr(Map<String,Object> map){
        if (map == null || map.size() <= 0 ) {
            return null;
        }
        StringBuilder msg = new StringBuilder();
        String temp = map.toString();
        msg.append("{");
        String str[] = temp.substring(1,temp.length()-1).split(",");
        String key_value[];
        String key = null;
        String value = null;
        for (int i = 0; i < str.length; i++){
            key_value = str[i].split("=");
            for (int j = 0;j < key_value.length;j++){
                if (j == 0){
                    key = key_value[j];
                    if (i > 0){
                        key = key_value[j].replaceFirst(" ", "");
                    }
                }else {
                    value =key_value[j];
                    msg.append("\"").append(key).append("\":\"").append(value).append("\"");
                    if (i < str.length - 1){
                        msg.append(",");                    }
                }
            }

        }
        msg.append("}");
        return msg.toString();
    }
    public static  Map<String,Object> strToMap(String msg){
        if (msg == null || "".equals(msg)){
            return null;
        }
        Map<String ,Object> map = new HashMap<>();
        String str[] = msg.substring(1,msg.length()-1).split(",");
        String key_value[];
        String key = null;
        String value = null;
        for (int i = 0;i < str.length;i++){
            key_value = str[i].split(":",2);
            for (int j = 0;j<key_value.length;j++){
                if (j==0){
                    key = key_value[j];
                    key = key.substring(1,key.length() - 1);
                }else {
                    value = key_value[j];
                    value = value.substring(1,value.length() - 1);
                    map.put(key,value);
                }
            }
        }
        return map;
    }
}
