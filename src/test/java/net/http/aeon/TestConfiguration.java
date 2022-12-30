package net.http.aeon;

import lombok.Getter;
import net.http.aeon.annotations.Comment;
import net.http.aeon.annotations.Options;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

@Getter
@Options(path = "src/test/java/net/http/aeon/", name = "config")
public class TestConfiguration {

    @Comment(comment = {"Test argument"})
    private Collection<String> collection;

    public TestConfiguration() {
        this.collection = new ArrayList<>();
        this.collection.add("a");
        this.collection.add("b");
    }
}
