/**
 * 
 */
package com.bugtrack.app.dao;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.bugtrack.app.model.MessageModel;
import com.bugtrack.app.model.UserModel;

/**
 * @author Administrator
 *
 */
public interface MessageClassRepository  extends JpaRepository<MessageModel, Integer>{
	List<MessageModel> findAllByReceiverAndReadtsIsNull(UserModel user);
	List<MessageModel> findAllByReceiver(UserModel user, Pageable pageable);
	List<MessageModel> findAllByReceiverAndStatus(UserModel user, Integer i);
	List<MessageModel> findAllByReceiver(UserModel user);
	List<MessageModel> findAllByReceiverAndStatusLessThan(UserModel user, int i);
	List<MessageModel> findAllByReceiverAndStatusLessThan(UserModel user, int i, Pageable pageable);
}
