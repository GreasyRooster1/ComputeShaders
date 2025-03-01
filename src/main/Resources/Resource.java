package main.Resources;

public abstract class Resource{
    public String path;
    public Resource(String path) {
        this.path = path;
        load();
    }

    public abstract void load();

    public abstract Object get();
}
