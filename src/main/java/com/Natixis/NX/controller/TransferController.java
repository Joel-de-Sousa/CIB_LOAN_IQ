package com.Natixis.NX.controller;

import com.Natixis.NX.entities.Transfer;
import com.Natixis.NX.repository.TransferRepository;
import com.Natixis.NX.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    private TransferService service;

    @Autowired
    private TransferRepository repository;

    public TransferController(TransferService service) {
        this.service = service;
    }

    @GetMapping("/transfers")
    List<Transfer> all() {
        return service.listAll();
    }

    @PostMapping("/saveTransfer")
    public void newTransfer(@RequestBody Transfer newTransfer) {
        service.save(newTransfer);
    }


    @GetMapping("/transfer/{id}")
    public Transfer findTransfer(@PathVariable Long id) {
        return service.findById(id);
    }

    @PutMapping("/transfer/{id}")
    public Optional<Transfer> editTransfer(@RequestBody Transfer newTransfer, @PathVariable Long id) {

        return repository.findById(id)
                .map(transfer -> {
                    transfer.setAmount(newTransfer.getAmount());
                    transfer.setTransferDate(newTransfer.getTransferDate());
                    return repository.save(transfer);
                });
    }

    @DeleteMapping("/transfer/{id}")
    public void deleteTransfer(@PathVariable Long id) {
        service.delete(id);
    }
}
