package com.sideproject.bookReader.service;

import com.sideproject.bookReader.model.Follow;
import com.sideproject.bookReader.model.User;
import com.sideproject.bookReader.repository.FollowRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;

    public List<Long> getFollowings(Long userId){


//        return followRepository.
        return null;
    }

    public void follow(User loginUser,  User target){

        if(!isFollowing(loginUser, target)){
            followRepository.save(Follow.builder()
                    .source(loginUser)
                    .target(target)
                    .build());
        }
    }

    public void unfollow(User loginUser, User target){
        if(isFollowing(loginUser, target)){
            Follow follow = retrieveFollowForService(loginUser, target);
            followRepository.delete(follow);
        }
    }

    public boolean isFollowing(User loginUser, User target){
        return followRepository.existsBySourceAndTarget(loginUser, target);
    }

    public Follow retrieveFollowForService(User loginUser, User target){
        return followRepository.findBySourceAndTarget(loginUser,target)
                .orElseThrow(() -> new RuntimeException());
    }
}
