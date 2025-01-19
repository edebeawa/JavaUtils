package top.edebe.util.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BiObject<L, R> {
    private final L left;
    private final R right;
}