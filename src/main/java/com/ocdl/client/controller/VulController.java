package com.ocdl.client.controller;

import com.google.gson.Gson;
import com.ocdl.client.dto.ReturnDto;
import com.ocdl.client.dto.VulDto;
import com.ocdl.client.service.HttpRequestService;
import com.ocdl.client.service.VulService;
import com.ocdl.client.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping(path = "/rest/vul")
public class VulController {

    @Autowired
    private VulService vulService;

    @Autowired
    private HttpRequestService httpRequestService;

    @Value("${project.refid}")
    private String projectRefId;

    @Value("${project.recycle.path}")
    private String recyclePath;

    private Gson gson = new Gson();

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public final Response predict(@RequestBody VulDto vulDto) {

         long startTime = System.currentTimeMillis();

        Response.Builder responseBuilder = Response.getBuilder();

        Response result = vulService.predict(vulDto);

        long endTime = System.currentTimeMillis();

        if (result.getCode() != 200) {
            return responseBuilder.setCode(Response.Code.ERROR)
                    .setMessage(result.getMessage()).build();
        }

        // format return data
        ReturnDto returnData = new ReturnDto(result.getData(), endTime-startTime);

        return responseBuilder.setCode(Response.Code.SUCCESS)
                .setData(returnData).build();

    }


    @ResponseBody
    @RequestMapping(path="/upload", method = RequestMethod.POST)
    public final Response uploaddata(@RequestBody VulDto vulDto) {

        Response.Builder responseBuilder = Response.getBuilder();
        vulDto.setProjectRefId(projectRefId);
        vulDto.setCreateAt(String.valueOf(System.currentTimeMillis()));

        String reply = httpRequestService.post(recyclePath, gson.toJson(vulDto));

        return responseBuilder.setCode(Response.Code.SUCCESS)
                .setData(reply).build();

    }

}
