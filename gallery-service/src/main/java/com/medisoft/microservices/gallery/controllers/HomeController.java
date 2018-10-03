package com.medisoft.microservices.gallery.controllers;


import com.medisoft.microservices.gallery.models.Gallery;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class HomeController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private Environment env;

    @RequestMapping("/")
    public String home(){
        return "Gallery Service running at port: " + env.getProperty("local.server.port");
    }

    @HystrixCommand(fallbackMethod = "fallback")
    @RequestMapping("/{id}")
    public Gallery getGallery(@PathVariable final int id){

        Gallery gallery = new Gallery();
        gallery.setId(id - 1);

        List<Object> images = restTemplate.getForObject("http://image-service/images/", List.class);

        List<Object> img = new ArrayList<>();

        if(images.size() >= id && id > 0) {
            Object image = images.get(id - 1);
            img.add(image);
        }

        gallery.setImages(img);

        return gallery;
    }

    @RequestMapping("/admin")
    public String homeAdmin(){
        return "Gallery admin services running at port: " + env.getProperty("local.server.port");
    }

    public Gallery fallback(int galleryId, Throwable hystrixCommand){
        return new Gallery(galleryId);
    }
}
