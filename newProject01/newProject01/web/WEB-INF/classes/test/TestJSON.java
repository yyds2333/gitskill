import com.fasterxml.jackson.databind.ObjectMapper;
import com.powernode.domain.Myself;
import org.junit.Test;

import java.io.IOException;

public class TestJSON {
    @Test
    public void tJson() throws IOException {
        // {"name":"yyds","age":25,"gender":"男"}
        String jsons = "{\"name\":\"yyds\",\"age\":25,\"gender\":\"男\"}";
        ObjectMapper omg = new ObjectMapper();
        Myself mf = omg.readValue(jsons,Myself.class);
        System.out.println(mf);
        System.out.println("===================================分割线捏===========================================");
        Myself mmf = new Myself("zyds",23,"女");
        String smmf = omg.writeValueAsString(mmf);
        System.out.println(smmf);
        Myself mmmf = omg.readValue(smmf, Myself.class);
        System.out.println("===================================分割线捏===========================================");
        System.out.println(mmmf);

    }
}
