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

    public List<Transfer> listAll(){
        return transferRepository.findAll();
    }

    public Transfer findById(Long id){
        return transferRepository.findById(id).get();
    }

    public void delete(Long id){
        transferRepository.deleteById(id);
    }

    public void save(Transfer transfer){
        transferRepository.save(transfer);
    }

}
