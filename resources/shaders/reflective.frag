#version 450 core

in vec2 uv;
in vec3 normal;
in vec3 position;

uniform vec3 cameraPos;
uniform samplerCube skybox;

out vec4 out_Color;

void main() {
    vec3 I = normalize(position - cameraPos);
    vec3 R = reflect(I, normalize(Normal));
    out_Color = vec4(texture(skybox, R).rgb, 1.0);
}