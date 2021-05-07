package kr.ac.jbnu.ampm.ksoyeon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
public class Controller {
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> responseEntity(HttpServletRequest request, @PathVariable String id){
        ResponseEntity<?> responseEntity = null;
        Map<String, Object> voMap = null;

        if(id != null && !id.equals("")){
            voMap = new HashMap<String, Object>();

            voMap.put("books",new LinkedHashMap<String, Object>(){{
                put("book3",new HashMap<String, Object>(){{
                    put("name","디지털공학개론");
                }});
                put("book2","소프트웨어공학개론");
                put("book1","마션");
            }});
            voMap.put("name","김소연");
            voMap.put("age","22");

            responseEntity = new ResponseEntity<>(voMap, HttpStatus.OK);
        }else{
            responseEntity = new ResponseEntity<>("NOT_FOUND",HttpStatus.NOT_FOUND);
        }
        return responseEntity;
    }
}

