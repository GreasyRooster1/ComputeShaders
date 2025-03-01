package main.Shaders;

public class ComputeShader extends Shader {
    public ComputeShader(String vertPath, String fragPath, String computePath) {
        super(vertPath, fragPath,computePath);
    }

    @Override
    protected void bindAttributes() {

    }
}
