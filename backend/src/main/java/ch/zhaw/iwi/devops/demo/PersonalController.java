package ch.zhaw.iwi.devops.demo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.logging.Logger;


@CrossOrigin
@RestController
public class PersonalController {
    private static final Logger logger = Logger.getLogger(PersonalController.class.getName());

    private Map<Integer, Personal> personals = new HashMap<Integer, Personal>();

    @EventListener(ApplicationReadyEvent.class)
    public void init() {
        this.personals.put(1,new Personal(1, "Timon Schneider", "Student"));
        this.personals.put(2,new Personal(2, "Steve Jobs", "CEO"));
        logger.info("Init Data");
    }
    
    @GetMapping("/personalcount")
    public int count() {
        return this.personals.size();
    }
    

    @GetMapping("/services/personal")
    public List<PathListEntry<Integer>> personal() {
        var result = new ArrayList<PathListEntry<Integer>>();
        for (var personal : this.personals.values()) {
            var entry = new PathListEntry<Integer>();
            entry.setKey(personal.getId(), "personalKey");
            entry.setName(personal.getTitle());
            entry.getDetails().add(personal.getDescription());
            entry.setTooltip(personal.getDescription());
            result.add(entry);
        }
        return result;
    }

    @GetMapping("/services/personal/{id}")
    public Personal getPersonal(@PathVariable Integer id) {
        return this.personals.get(id);
    }

    @PostMapping("/services/personal")
    public void createPersonal(@RequestBody Personal personal) {
        var newId = this.personals.keySet().stream().max(Comparator.naturalOrder()).orElse(0) + 1;
        personal.setId(newId);
        this.personals.put(newId, personal);
    }

    @PutMapping("/services/personal/{id}")
    public void createPersonal(@PathVariable Integer id, @RequestBody Personal personal) {
        personal.setId(id);
        this.personals.put(id, personal);
    }

    @DeleteMapping("/services/personal/{id}")
    public Personal deletePersonal(@PathVariable Integer id) {
        return this.personals.remove(id);
    }
}
