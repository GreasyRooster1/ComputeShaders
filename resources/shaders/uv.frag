#version 450 core

in vec2 uv;

out vec4 out_Color;

uniform sampler2D textureSampler;

void main(){
    out_Color = vec4(uv.x,uv.y,0.0,1.0);
}