import com.alibaba.fastjson.JSONObject;
import com.dudu.domain.LearnResource;

public class JsonTest {
    public static void main(String[] args) {

        JSONObject jo=new JSONObject();
        jo.put("rows",new LearnResource());
        jo.put("total",2);
        jo.put("records",4);
        String json = "";
        json=JSONObject.toJSONStringWithDateFormat(jo,"yyyy-MM-dd");
        System.out.println(json);
    }
}
