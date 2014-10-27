#version 110

uniform mat4 u_projectionViewMatrix;

attribute vec4 in_Position;
attribute vec4 in_Color;
attribute vec2 in_TextureCoord;

varying vec4 pass_Color;
varying vec2 pass_TextureCoord;

void main(void) {

	gl_Position = u_projectionViewMatrix * in_Position;
	

	pass_Color = in_Color;
	pass_TextureCoord = in_TextureCoord;

}