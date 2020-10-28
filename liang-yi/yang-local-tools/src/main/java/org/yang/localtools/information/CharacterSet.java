package org.yang.localtools.information;

/**
 * 字符集
 */
public enum CharacterSet {

    UTF8("utf8","");

    private String name;
    private String msg;

    CharacterSet(String name, String msg) {
        this.name = name;
        this.msg = msg;
    }

    public String getName() {
        return name;
    }
}
