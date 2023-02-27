package com.example.implauthenticationserver.service;

import com.example.implauthenticationserver.entity.Otp;
import com.example.implauthenticationserver.entity.User;
import com.example.implauthenticationserver.repository.OtpRepository;
import com.example.implauthenticationserver.repository.UserRepository;
import com.example.implauthenticationserver.util.GenerateCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OtpRepository otpRepository;
    @Autowired
    private PasswordEncoder encoder;

    public void addUser(User user) {
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void auth(User user) {
        Optional<User> optionalUser = userRepository.findByUsername(user.getUsername());

        if(optionalUser.isPresent()) {
            User userInDB = optionalUser.get();
            if(encoder.matches(user.getPassword(), userInDB.getPassword())) {
                renewOtp(userInDB);
            } else {
                throw new BadCredentialsException("Bad credential");
            }
        } else {
            throw new BadCredentialsException("Bad credential");
        }
    }

    private void renewOtp(User userInDB) {
        String code = GenerateCodeUtil.generateCode();

        Optional<Otp> optUserOtp = otpRepository.findByUsername(userInDB.getUsername());

        if(optUserOtp.isPresent()) {
            Otp otp = optUserOtp.get();
            otp.setCode(code);
        } else {
            Otp otp = new Otp();
            otp.setUsername(userInDB.getUsername());
            otp.setCode(code);
            otpRepository.save(otp);
        }
    }

    public boolean check(Otp optToValidate) {
        Optional<Otp> optionalOtp = otpRepository.findByUsername(optToValidate.getUsername());
        if(optionalOtp.isPresent()) {
            Otp otp = optionalOtp.get();
            if(otp.getCode().equals(optToValidate.getCode())) return true;
        }

        return false;
    }
}
