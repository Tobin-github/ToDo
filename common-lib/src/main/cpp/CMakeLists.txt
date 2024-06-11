# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.10.2)

# Declares and names the project.

project("libtest")

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        libtest

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        native-lib.cpp)

# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.
# 搜索指定的预构建库，并将路径存储为变量。
# 因为默认情况下，CMake在搜索路径中包括系统库，所以您只需要指定要添加的公共NDK库的名称。
# CMake在完成其构建之前验证库是否存在。
find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that you want CMake to locate.
        # 指定要CMake定位的NDK库的名称
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.
# 指定CMake应链接到目标库的库。您可以链接多个库，例如在此生成脚本中定义的库、预生成的第三方库或系统库
target_link_libraries( # Specifies the target library.
        libtest

        # Links the target library to the log library included in the NDK.
        # 将目标库链接到NDK中包含的日志库。
        ${log-lib})