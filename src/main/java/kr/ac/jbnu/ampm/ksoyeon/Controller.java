package kr.ac.jbnu.ampm.ksoyeon;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController

public class Controller {
    // get부터 id로 받는 것 --> type으로 받기, Post,delete
    // 1. testdb 2.get all 3. get id 4.post 5.delete
    public static HashMap<String, ArrayList<Map<String, Object>>> testDBHashMap = new HashMap<String, ArrayList<Map<String, Object>>>();

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getAllResponseEntity(HttpServletRequest request) {
        ResponseEntity<?> responseEntity = null;

        if (!testDBHashMap.isEmpty()) {
            responseEntity = new ResponseEntity<>(testDBHashMap, HttpStatus.OK);

        } else {
            responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getResponseEntity(HttpServletRequest request, @PathVariable String id) {
        ResponseEntity<?> responseEntity = null;

        if (!testDBHashMap.isEmpty()) {
            if (id != null && !id.equals("") && testDBHashMap.containsKey(id)) {
                responseEntity = new ResponseEntity<>(testDBHashMap.get(id), HttpStatus.OK);

            } else {
                responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
            }

        } else {
            responseEntity = new ResponseEntity<>("NOT_FOUND", HttpStatus.NOT_FOUND);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/post/{id}", method = RequestMethod.POST, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> postResponseEntity(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String, Object> requestMap) {
        ResponseEntity<?> responseEntity = null;
        ArrayList<Map<String, Object>> postValueArrayList = null;

        if (id != null && !id.equals("")) {

            if(testDBHashMap.containsKey(id)) {
                postValueArrayList = testDBHashMap.get(id);
            } else {
                postValueArrayList = new ArrayList<Map<String, Object>>();
            }

            postValueArrayList.add(requestMap);

            if (testDBHashMap.containsKey(id)) {
                testDBHashMap.replace(id, postValueArrayList);
            } else {
                testDBHashMap.put(id, postValueArrayList);
            }

            responseEntity = new ResponseEntity<>(requestMap, HttpStatus.OK);

        } else {
            responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteResponseEntity(HttpServletRequest request, @PathVariable String id) {
        ResponseEntity<?> responseEntity = null;

        if (id != null && !id.equals("")) {
            if (testDBHashMap.containsKey(id)) {
                testDBHashMap.remove(id);
                responseEntity = new ResponseEntity<>("", HttpStatus.OK);

            } else {
                responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
            }
        } else {
            responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.BAD_REQUEST);
        }
        return responseEntity;
    }

    @RequestMapping(value = "/put/{id}", method = RequestMethod.PUT, produces = "application/json")
    @ResponseBody
    public ResponseEntity<?> deleteResponseEntity(HttpServletRequest request, @PathVariable String id, @RequestBody Map<String, Object> requestMap)  {
        ResponseEntity<?> responseEntity = null;
        ArrayList<Map<String, Object>> postValueArrayList = null;
        postValueArrayList = testDBHashMap.get(id);

        if (!testDBHashMap.isEmpty()) { //testDBHashMap 리스트에 요소가 있는지 없는지를 판정하여 비어있지 않은 경우
            if (id != null  && !id.equals("") && testDBHashMap.containsKey(id)){ //id는 null이 아니고 ""가 아니고  id 값을 갖고 있다면
                for (Map <String, Object> map : postValueArrayList) {
                    if (requestMap.keySet().equals(map.keySet())) {
                        postValueArrayList.set(postValueArrayList.indexOf(map), requestMap);
                        //값 변경하는 부분
                        //set(int index,E  element)리스트의 지정된 위치에 있는 요소를, 지정된 요소로 옮겨놓음
                        //indexOf(Object  elem) equals 메서드를 사용해 동일한지 어떤지를 판정하면서, 지정된 인수와 같은 내용의 요소를 선두로부터 검색
                        testDBHashMap.replace(id, postValueArrayList);
                        responseEntity = new ResponseEntity<>(requestMap, HttpStatus.OK);
                    } else {
                        responseEntity = new ResponseEntity<>("NOT_CONTAIN", HttpStatus.NOT_FOUND);
                    }
                }
            }

        } else {
              responseEntity = new ResponseEntity<>("BAD_REQUSET", HttpStatus.BAD_REQUEST);
            }

        return responseEntity;
    }
}




