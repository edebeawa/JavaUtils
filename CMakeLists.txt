cmake_minimum_required(VERSION 3.19)
project(JavaUtils)

set(CMAKE_CXX_STANDARD 14)

aux_source_directory(. DIR_SRCS)
file(GLOB_RECURSE DIR_SRCS "./include/*" "./project/*")

add_library(JavaUtils SHARED ${DIR_SRCS})