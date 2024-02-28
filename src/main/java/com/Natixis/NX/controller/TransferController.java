package com.Natixis.NX.controller;

import com.Natixis.NX.entities.Transfer;
import com.Natixis.NX.repository.TransferRepository;
import com.Natixis.NX.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class TransferController {

    @Autowired
    private TransferService service;

    @Autowired
    private TransferRepository repository;

    private final String HOME = "pagina-inicial";

    public TransferController(TransferService service) {
        this.service = service;
    }

    /**
     * Home request and shows all the current transfers stored in the DB
     * @return
     */
    @GetMapping("/")
    public String home(Model model){
        List<Transfer> transfers = service.listAll();
        model.addAttribute("transfers", transfers);
        return HOME;}

    /**
     * This requests points to a new html page to create a new transfer
     * @param model
     * @return
     */
    @GetMapping("/createTransfer")
    public String createTransfer(Model model){
        model.addAttribute("transfer", new Transfer());
        return "new-transfer";
    }

    /**
     * This requests saves the new transfer and direct to home page
     * @param transfer
     * @return
     */
    @PostMapping("/saveTransfer")
    public String saveTransfer(@ModelAttribute Transfer transfer, Model model) throws Exception {
        try {
            double bankFee = transfer.calculateTransferFee(transfer.getTransferDate(), transfer.getAmount());
            transfer.setBankFee(bankFee);
            service.save(transfer);
            return "redirect:/";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "new-transfer";
        }
    }

    /**
     * This requests takes you to the edit page with the ID from the table form
     * @param model
     * @param id
     * @return
     */
    @GetMapping("/editTransfer/{id}")
    public String editTransfer(Model model, @PathVariable Long id) {
        Transfer findById = service.findById(id);
        model.addAttribute("editTransfer", findById);
        return "edit-transfer";
    }

    /**
     * This method saves the edit to the repository
     * @param transfer
     * @return
     */
    @PostMapping("/saveEditTransfer")
    public String saveEditTransfer(@ModelAttribute Transfer transfer, Model model) throws Exception {
        try{
            Transfer t1 =service.findById(transfer.getId());
            t1.setAmount(transfer.getAmount());
            t1.setTransferDate(transfer.getTransferDate());
            service.save(t1);
        return "redirect:/";
    } catch (Exception e) {
        model.addAttribute("error", e.getMessage());
            model.addAttribute("editTransfer", transfer);
        return "edit-transfer";
        }
    }

    /**
     * This requests deletes a transfer from the table form
     * @param id
     * @return
     */
    @GetMapping("/deleteTransfer/{id}")
    public String deleteTransfer(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
