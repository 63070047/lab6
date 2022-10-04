package com.example.lab6.controller;


import com.example.lab6.pojo.Wizard;
import com.example.lab6.pojo.Wizards;
import com.example.lab6.repository.WizardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class WizardController {
    @Autowired
    private WizardService wizardService;

    @RequestMapping(value ="/wizards", method = RequestMethod.GET)
    public ResponseEntity<?> getWizards() {
        List<Wizard> wizards = wizardService.retrieveWizard();
        return ResponseEntity.ok(wizards);
    }
    @RequestMapping(value ="/addWizard", method = RequestMethod.POST)
    public ResponseEntity<?> createWizard(@RequestBody Wizard wizard) {
        Wizard n = wizardService.createWizard(wizard);
        return ResponseEntity.ok(n);
    }
    @RequestMapping(value ="/updateWizard", method = RequestMethod.POST)
    public boolean updateWizard(@RequestBody Wizard wizard) {
        Wizard data1 = wizardService.retrieveWizardById(wizard.get_id());
        if(wizard != null) {
            wizardService.updateWizard(new Wizard(data1.get_id(), wizard.getName(), wizard.getSex(), wizard.getSchool(), wizard.getHouse(), wizard.getPosition(), wizard.getMoney()));
            return true;
        }else {
            return false;
        }
    }
    @RequestMapping(value ="/deleteWizard", method = RequestMethod.POST)
    public boolean deleteWizard(@RequestBody Wizard wizard) {
        Wizard data = wizardService.retrieveWizardByName(wizard.getName());
        boolean status = wizardService.deleteWizard(data);
        return status;
    }
}
