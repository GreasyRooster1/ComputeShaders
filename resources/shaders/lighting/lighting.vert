#version 450 core

in vec3 position;
in vec2 texCoord;
in vec3 norm;

uniform mat4 projection;
uniform mat4 view;
uniform mat4 model;

out vec2 uv;
out vec3 normal;
out vec3 position;

void main() {
    gl_Position = projection * view * model * vec4(position,1.0);
    uv = texCoord;
    normal = norm;
    position = gl_Position;
}