package model;

import java.io.Serializable;

public class AbstractModel implements Serializable {
    protected Integer id;
    private static final long serialVersionUID = 1L;

    public AbstractModel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
