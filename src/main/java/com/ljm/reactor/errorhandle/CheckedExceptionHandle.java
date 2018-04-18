package com.ljm.reactor.errorhandle;

import reactor.core.Exceptions;
import reactor.core.publisher.Flux;

import java.io.FileNotFoundException;

/**
 * 非受检异常会被Reactor传播
 * 而受检异常必须被用户代码try catch，为了使用异常传播机制和异常处理机制，可以使用如下步骤处理：
 * 1. 使用 Exceptions.propagate将受检异常包装为非受检异常并重新抛出传播出去
 * 2. onError、error回调等异常处理操作获取到异常之后，可以调用Exceptions.unwrap取得原受检的异常
 *
 * @author 李佳明 https://github.com/pkpk1234
 * @date 2018-04-19
 */
public class CheckedExceptionHandle {
    public static void main(String[] args) {
        Flux<String> flux = Flux.just("abc", "def", "exception", "ghi")
                .map(s -> {
                    try {
                        return doSth(s);
                    } catch (FileNotFoundException e) {
                        // 包装并传播异常
                        throw Exceptions.propagate(e);
                    }
                });
        //abc、def正常打印，然后打印 参数异常
        flux.subscribe(System.out::println,
                e -> {
                    //获取原始受检异常
                    Throwable sourceEx = Exceptions.unwrap(e);
                    //判断异常类型并处理
                    if (sourceEx instanceof FileNotFoundException) {
                        System.err.println(((FileNotFoundException) sourceEx).getMessage());
                    } else {
                        System.err.println("Other exception");
                    }
                });

    }

    public static String doSth(String str) throws FileNotFoundException {
        if ("exception".equals(str)) {
            throw new FileNotFoundException("参数异常");
        } else {
            return str.toUpperCase();
        }
    }
}
