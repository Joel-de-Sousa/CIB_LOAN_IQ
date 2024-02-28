package com.Natixis.NX.service;

import com.Natixis.NX.entities.Transfer;
import com.Natixis.NX.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransferService {

    @Autowired
    TransferRepository transferRepository;

    /**
     * Lists all the transfers
     * @return
     */
    public List<Transfer> listAll(){
        return transferRepository.findAll();
    }

    /**
     * Finds transfer by ID
     * @param id
     * @return
     */
    public Transfer findById(Long id){
        return transferRepository.findById(id).get();
    }

    /**
     * Deletes a transfer by their ID
     * @param id
     */
    public void delete(Long id){
        transferRepository.deleteById(id);
    }

    /**
     * Saves a new or edited instance of transfer
     * @param transfer
     */
    public void save(Transfer transfer) throws Exception{
        try{
        double bankFee = transfer.calculateTransferFee(transfer.getTransferDate(), transfer.getAmount());
        transfer.setBankFee(bankFee);
        transferRepository.save(transfer);
    } catch (Exception e){
            e.printStackTrace();
            throw new Exception(e.getMessage());
        }
    }
}
