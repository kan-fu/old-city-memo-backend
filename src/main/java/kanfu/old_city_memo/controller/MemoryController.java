package kanfu.old_city_memo.controller;

import java.util.List;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kanfu.old_city_memo.model.Memory;
import kanfu.old_city_memo.model.User;
import kanfu.old_city_memo.repo.MemoryRepo;
import lombok.AllArgsConstructor;
// import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
@CrossOrigin(origins = "*")
// @Slf4j
public class MemoryController {
    private MemoryRepo memoryRepo;

    @GetMapping("/memories")
    List<Memory> getAll() {
        return memoryRepo.findAll();
    }

    @PostMapping("/memories")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    Memory create(@RequestBody Memory memory) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        memory.setUsername(user.getUsername());
        return memoryRepo.save(memory);
    }

    @GetMapping("/memories/{id}")
    Memory getById(@PathVariable String id) {
        return memoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No memory found with id: " + id));
    }

    @PutMapping("/memories/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    Memory update(@PathVariable String id, @RequestBody Memory memory) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Memory memoryToUpdate = memoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No memory found with id: " + id));
        if (user.getRole().equals("ROLE_USER")
                && !user.getUsername().equals(memoryToUpdate.getUsername())) {
            throw new MemoryUnauthorizedException("You are not allowed to update this memory");
        }
        memoryToUpdate.setTitle(memory.getTitle());
        memoryToUpdate.setYear(memory.getYear());
        memoryToUpdate.setDescription(memory.getDescription());
        memoryToUpdate.setLat(memory.getLat());
        memoryToUpdate.setLon(memory.getLon());
        memoryToUpdate.setPicturePath(memory.getPicturePath());
        return memoryRepo.save(memoryToUpdate);
    }

    @DeleteMapping("/memories/{id}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    void delete(@PathVariable String id) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Memory memoryToDelete = memoryRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No memory found with id: " + id));
        if (user.getRole().equals("ROLE_USER")
                && !user.getUsername().equals(memoryToDelete.getUsername())) {
            throw new MemoryUnauthorizedException("You are not allowed to delete this memory");
        }
        memoryRepo.delete(memoryToDelete);
    }


}
