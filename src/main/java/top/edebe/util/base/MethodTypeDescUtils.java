package top.edebe.util.base;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
public class MethodTypeDescUtils {
    public static final int MAX_ARRAY_TYPE_DESC_DIMENSIONS = 255;
    public static final char JVM_SIGNATURE_ARRAY = '[';
    public static final char JVM_SIGNATURE_BYTE = 'B';
    public static final char JVM_SIGNATURE_CHAR = 'C';
    public static final char JVM_SIGNATURE_CLASS = 'L';
    public static final char JVM_SIGNATURE_ENDCLASS = ';';
    public static final char JVM_SIGNATURE_ENUM = 'E';
    public static final char JVM_SIGNATURE_FLOAT = 'F';
    public static final char JVM_SIGNATURE_DOUBLE = 'D';
    public static final char JVM_SIGNATURE_FUNC = '(';
    public static final char JVM_SIGNATURE_ENDFUNC = ')';
    public static final char JVM_SIGNATURE_INT = 'I';
    public static final char JVM_SIGNATURE_LONG = 'J';
    public static final char JVM_SIGNATURE_SHORT = 'S';
    public static final char JVM_SIGNATURE_VOID = 'V';
    public static final char JVM_SIGNATURE_BOOLEAN = 'Z';

    public static boolean verifyUnqualifiedClassName(String name) {
        for (int index = 0; index < name.length(); index++) {
            char ch = name.charAt(index);
            if (ch < 128) {
                if (ch == '.' || ch == ';' || ch == '[' ) {
                    return false;   // do not permit '.', ';', or '['
                }
                if (ch == '/') {
                    // check for '//' or leading or trailing '/' which are not legal
                    // unqualified name must not be empty
                    if (index == 0 || index + 1 >= name.length() || name.charAt(index + 1) == '/') {
                        return false;
                    }
                }
            } else {
                index ++;
            }
        }
        return true;
    }

    public static int skipOverFieldSignature(String descriptor, int start, int end, boolean voidOK) {
        int arrayDim = 0;
        int index = start;
        while (index < end) {
            switch (descriptor.charAt(index)) {
                case JVM_SIGNATURE_VOID: if (!voidOK) { return index; }
                case JVM_SIGNATURE_BOOLEAN:
                case JVM_SIGNATURE_BYTE:
                case JVM_SIGNATURE_CHAR:
                case JVM_SIGNATURE_SHORT:
                case JVM_SIGNATURE_INT:
                case JVM_SIGNATURE_FLOAT:
                case JVM_SIGNATURE_LONG:
                case JVM_SIGNATURE_DOUBLE:
                    return index - start + 1;
                case JVM_SIGNATURE_CLASS:
                    // Skip leading 'L' and ignore first appearance of ';'
                    index++;
                    int indexOfSemi = descriptor.indexOf(';', index);
                    if (indexOfSemi != -1) {
                        String unqualifiedName = descriptor.substring(index, indexOfSemi);
                        boolean legal = verifyUnqualifiedClassName(unqualifiedName);
                        if (!legal) {
                            return 0;
                        }
                        return index - start + unqualifiedName.length() + 1;
                    }
                    return 0;
                case JVM_SIGNATURE_ARRAY:
                    arrayDim++;
                    if (arrayDim > MAX_ARRAY_TYPE_DESC_DIMENSIONS) {
                        throw new IllegalArgumentException(String.format("Cannot create an array type descriptor with more than %d dimensions", MAX_ARRAY_TYPE_DESC_DIMENSIONS));
                    }
                    // The rest of what's there better be a legal descriptor
                    index++;
                    voidOK = false;
                    break;
                default:
                    return 0;
            }
        }
        return 0;
    }

    public static List<String> parseMethodDescriptor(String descriptor) {
        int cur = 0, end = descriptor.length();
        ArrayList<String> ptypes = new ArrayList<>();

        if (cur >= end || descriptor.charAt(cur) != '(')
            throw new IllegalArgumentException("Bad method descriptor: " + descriptor);

        ++cur;  // skip '('
        while (cur < end && descriptor.charAt(cur) != ')') {
            int len = skipOverFieldSignature(descriptor, cur, end, false);
            if (len == 0)
                throw new IllegalArgumentException("Bad method descriptor: " + descriptor);
            ptypes.add(descriptor.substring(cur, cur + len));
            cur += len;
        }
        if (cur >= end)
            throw new IllegalArgumentException("Bad method descriptor: " + descriptor);
        ++cur;  // skip ')'

        int rLen = skipOverFieldSignature(descriptor, cur, end, true);
        if (rLen == 0 || cur + rLen != end)
            throw new IllegalArgumentException("Bad method descriptor: " + descriptor);
        ptypes.add(0, descriptor.substring(cur, cur + rLen));
        return ptypes;
    }
}