package top.tobin.component.framework;

import java.util.Iterator;
import java.util.ServiceLoader;

/**
 * 接口实现类工厂
 *
 * @param <I> 要被寻找实现类的接口
 */
public class ImplClassFactory<I> implements Iterator<I> {
    private final Iterator<I> mIterator;
    private final ServiceLoader<I> mLoader;

    public ImplClassFactory(Class<I> interfaceClass) {
        this(interfaceClass, Thread.currentThread().getContextClassLoader());
    }

    public ImplClassFactory(Class<I> interfaceClass, ClassLoader loader) {
        if (interfaceClass == null || !interfaceClass.isInterface()) {
            throw new IllegalArgumentException("interfaceClass must be a Interface!");
        }
        mLoader = ServiceLoader.load(interfaceClass, loader);
        mIterator = mLoader.iterator();
    }

    public void reload() {
        mLoader.reload();
    }

    @Override
    public boolean hasNext() {
        return mIterator.hasNext();
    }

    @Override
    public I next() {
        return mIterator.next();
    }

}
