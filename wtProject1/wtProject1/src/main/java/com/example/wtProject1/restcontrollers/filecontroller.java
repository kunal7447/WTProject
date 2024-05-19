package com.example.wtProject1.restcontrollers;

import com.example.wtProject1.entity.ImageData;
import com.example.wtProject1.service.StorageService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/image")
public class filecontroller {


    @Autowired
    private StorageService service;
    @Autowired
    private EntityManager em;
    @GetMapping("/input")
    public String hello(Model themod){
        themod.addAttribute("num", 0);
     //   System.out.println(userId);
        // Retrieve all values from the image_data table
        String sqlQuery = "SELECT * FROM image_data";
        Query query = em.createNativeQuery(sqlQuery, ImageData.class);
        List<ImageData> imageDataList = query.getResultList();

        // Add the list of image data to the model
        themod.addAttribute("imageDataList", imageDataList);
        //themod.addAttribute("userId", userId);

        return "upload";
    }
    //@PostMapping("/uploaded")
    @Transactional
    @GetMapping("/gett")
    public String gett(@RequestParam("id") int id) throws IOException {

        long userId = UserController.user.getId();
        String tableName = "Cart_" + userId;
        String sqlQuery = "INSERT INTO " + tableName + " (fav) VALUES (:id)";
        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("id", id);
        query.executeUpdate();

        return "redirect:/image/input";

    }
    @PostMapping("/uploaded")
    public String uploadImage(@RequestParam("image") MultipartFile file) throws IOException {

        String uploadImage = service.uploadImage(file);
        if(uploadImage==null){
            System.out.println("why");
        }
        ResponseEntity.status(HttpStatus.OK)
                .body(uploadImage);
        return "redirect:/image/input";

    }
    @PostMapping("/get-lang")
    @Transactional
    public String getlang(@RequestParam("lang111") String s) {

        // Retrieve the maximum ID from the image_data table
        String sqlQuery = "SELECT max(id) FROM image_data";
        Query query = em.createNativeQuery(sqlQuery);
        Object result = query.getSingleResult();
        Long maxId = result != null ? ((Number) result).longValue() : null;

        // Insert values into the image_c table
        sqlQuery = "INSERT INTO image_c (id1, language) VALUES (:maxId, :s)";
        query = em.createNativeQuery(sqlQuery);
        query.setParameter("s", s);
        query.setParameter("maxId", maxId);
        int rowsAffected = query.executeUpdate(); // Execute the insert query

        System.out.println("Rows affected: " + rowsAffected);

        return "redirect:/image/input";
    }




    @PostMapping("/favt")
    @Transactional
    public String getlang(Model model) {
        // Get the user's ID
        long userId = UserController.user.getId();

        // Construct the table name based on the user's ID
        String tableName = "Cart_" + userId;

        // Construct the SQL query to select data from the image_data table
        String sqlQuery = "SELECT * FROM image_data WHERE id IN (SELECT fav FROM " + tableName + ")";

        // Execute the SQL query
        Query query = em.createNativeQuery(sqlQuery, ImageData.class);
        List<ImageData> imageDataList = query.getResultList();

        // Add the retrieved data to the model
        model.addAttribute("imageDataList", imageDataList);

        // Send the result to fav.html
        return "fav";
    }




    @PostMapping("/language")
    @Transactional
    public String lklk(@RequestParam("language") String s, Model themod, RedirectAttributes redirectAttributes) {
        // Retrieve the image data based on the selected language
        String sqlQuery = "SELECT image_data.id, image_data.name, image_data.type " +
                "FROM image_data " +
                "WHERE image_data.id IN (SELECT image_c.id1 FROM image_c WHERE language = :s)";

        Query query = em.createNativeQuery(sqlQuery);
        query.setParameter("s", s);

        List<Object[]> resultList = query.getResultList();

        // Create a list to hold ImageData objects
        List<ImageData> imageDataList = new ArrayList<>();

        // Iterate over the result list and populate the imageDataList
        for (Object[] row : resultList) {
            Long id = ((Number) row[0]).longValue(); // Cast to Number and then Long
            String name = (String) row[1];
            String type = (String) row[2];

            // Create ImageData object and add it to the list
            ImageData imageData = new ImageData(id, name, type);
            imageDataList.add(imageData);
        }
    //    themod.addAttribute("userId", userId);
        themod.addAttribute("imageDataList", imageDataList);
        return "upload";
    }





    @GetMapping("/{fileName}/")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String fileName) {
        byte[] fileData = service.downloadImage(fileName);

        // Check if fileData is not null
        if (fileData != null) {
            // Set content type to PDF
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);

            // Set content disposition as attachment to force download
            headers.setContentDispositionFormData("attachment", fileName);

            // Return the PDF file with appropriate headers
            return ResponseEntity.ok().headers(headers).body(fileData);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }



}
