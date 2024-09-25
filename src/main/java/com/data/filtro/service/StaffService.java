package com.data.filtro.service;

import com.data.filtro.model.Account;
import com.data.filtro.model.Staff;
import com.data.filtro.repository.StaffRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

//import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StaffService {

    private final StaffRepository staffRepository;

    public Staff getById(long id) {
        return staffRepository.findById(id);
    }


    public List<Staff> getAll() {
        return staffRepository.findAll();
    }


    public Page<Staff> getAllPaging(Pageable pageable) {
        return staffRepository.findAll(pageable);
    }
    public List<Staff> getActiveStaff(int status){
        return staffRepository.activeStaffs(status);
    }


    public void create(Staff staff) {
        staffRepository.save(staff);
    }

    public void update(Staff staff) {
        Staff newStaff = getById(staff.getId());
        newStaff.setName(staff.getName());
        newStaff.setDob(staff.getDob());
        newStaff.setStatus(staff.getStatus());
        newStaff.setSex(staff.getSex());
        newStaff.setPhoneNumber(staff.getPhoneNumber());

        newStaff.setAccountName(staff.getAccountName());
        newStaff.setPassword(staff.getPassword());
        newStaff.setRoleNumber(2);
        staffRepository.save(newStaff);
    }

    @Transactional
    public void delete(long id) {
        staffRepository.delete(id);
    }

}
