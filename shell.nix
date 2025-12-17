{ pkgs ? import <nixpkgs> {} }:

pkgs.mkShell {
  name = "SmartBook Java";
  buildInputs = with pkgs; [
    javaPackages.compiler.openjdk21
    gradle
  ];
}
