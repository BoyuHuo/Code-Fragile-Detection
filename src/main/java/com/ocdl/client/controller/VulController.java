package com.ocdl.client.controller;

import com.google.gson.Gson;
import com.ocdl.client.dto.ReturnDto;
import com.ocdl.client.dto.VulDto;
import com.ocdl.client.service.VulService;
import com.ocdl.client.service.impl.S3Service;
import com.ocdl.client.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Controller
@RequestMapping(path = "/rest/vul")
public class VulController {

    @Autowired
    private VulService vulService;

    @Autowired
    private S3Service s3Service;

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
        vulDto.setCreateAt(String.valueOf(System.currentTimeMillis()));

        try {
            Files.write(Paths.get(vulDto.getCreateAt()), gson.toJson(vulDto).getBytes());
        } catch (IOException e) {
            return responseBuilder.setCode(Response.Code.ERROR)
                    .setMessage(e.getMessage()).build();
        }
        File file = new File(vulDto.getCreateAt());
        s3Service.uploadObject(vulDto.getCreateAt(), file);
        file.delete();

        return responseBuilder.setCode(Response.Code.SUCCESS)
                .setData(null).build();

    }

}
