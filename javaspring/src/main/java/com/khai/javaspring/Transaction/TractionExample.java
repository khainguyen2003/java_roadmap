package com.khai.javaspring.Transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class userServiceImpl implements userService {
    @Autowired
    private userRepository userRepository;

    @Transactional(isolation = Isolation.SERIALIZABLE, )
    public void updateMail(Long id, String mail) {
        User user = userRepository.findById(id).get();
        user.setMail(name);
    }
}
