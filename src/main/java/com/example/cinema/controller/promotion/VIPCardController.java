package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.VIPCardChargeForm;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestBody VIPCardForm vipCardForm){
        return vipService.addVIPCard(vipCardForm);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardChargeForm vipCardChargeForm){
        return vipService.charge(vipCardChargeForm);
    }


}
