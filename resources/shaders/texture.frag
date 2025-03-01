#version 450 core

in vec2 uv;
in vec3 norm;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(){
    out_Color = texture(textureSampler,vec2(uv.x,uv.y));
}