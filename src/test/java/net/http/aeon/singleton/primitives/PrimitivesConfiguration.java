package net.http.aeon.singleton.primitives;

import lombok.Getter;
import net.http.aeon.annotations.CommentedArgument;
import net.http.aeon.annotations.Options;
import net.http.aeon.handler.types.Emphasizing;

@Getter
@Options(path = "src/test/java/net/http/aeon/output/singleton/", name = "primitives-test")
@CommentedArgument(comment = "das ist ein test", type = Emphasizing.TOP)
public final class PrimitivesConfiguration {

    private final int value;
    private final String value2 = "abc";

    public PrimitivesConfiguration() {
        this.value = 300;
    }
}
