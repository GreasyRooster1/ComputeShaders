#version 450 core

in vec2 uv;
in vec3 normal;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(){
    out_Color = vec4(normal/2. + 0.5,1.0);
}