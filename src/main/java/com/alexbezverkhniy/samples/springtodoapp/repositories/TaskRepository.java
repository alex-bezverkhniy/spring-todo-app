package com.alexbezverkhniy.samples.springtodoapp.repositories;

import com.alexbezverkhniy.samples.springtodoapp.domain.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Alex Bezverkhniy on 2/21/18.
 */
@Repository
public interface TaskRepository extends CrudRepository<Task, Long>, PagingAndSortingRepository<Task, Long> {
    List<Task> findByTitle(String title);
    Page<Task> findByTitle(String title, Pageable pageable);
}
