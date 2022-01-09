package dev.gusevang.tree;

import com.sun.jdi.DoubleValue;
import dev.gusevang.threadpool.ThreadPool;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class TaskNode<T, T1> extends Node<T1> {
    private T method;    //divide, add, ... etc.

    public TaskNode(T typeOfMethod, Node<T1> nodeleft, Node<T1> noderight) { //for zip/(reduce) operations
        method = typeOfMethod;
        right = noderight;
        left = nodeleft;
    }

    public TaskNode(T typeOfMethod, Node<T1> node) { //for map operations
        method = typeOfMethod;
        right = left = node;
    }

    @Override
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> calculate(CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> val1, CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> val2) {
        if (val1 == null || val2 == null || val1.isEmpty() || val2.isEmpty()) {
            return null;
        }
        if (!(val1.get(0).get(0) instanceof Integer || val1.get(0).get(0) instanceof Double || val1.get(0).get(0) instanceof Float)) {
            return null;
        }
        int y = Math.min(val1.size(), val2.size());
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> result = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> vall1 = (CopyOnWriteArrayList<CopyOnWriteArrayList<Double>>) (Object) val1;
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> vall2 = (CopyOnWriteArrayList<CopyOnWriteArrayList<Double>>) (Object) val2;
        y = 0;
        if (method.equals(Map.divide)) {
            for (var i : vall1) {
                result.add(new CopyOnWriteArrayList<>(new Double[]{(double) i.get(0) / i.get(1)}));
            }
        } else if (method.equals(Map.add)) {
            for (var i : vall1) {
                result.add(new CopyOnWriteArrayList<>(new Double[]{(double) i.get(0) + i.get(1)}));
            }
        } else if (method.equals(Map.multiply)) {
            for (List<Double> i : vall1) {
                /*try{
                    Thread.sleep(10);
                }
                catch (InterruptedException e) {
                    e.printStackTrace();
                }*/
                result.add(new CopyOnWriteArrayList<>(new Double[]{(double) i.get(0) * i.get(1)}));
            }
        } else if (method.equals(Map.exponentiate)) {
            for (var i : vall1) {
                result.add(new CopyOnWriteArrayList<>(new Double[]{Math.pow(i.get(0), i.get(1))}));
            }
        } else if (method.equals(Reduce.max)) {
            for (var val : vall1) {
                double max = 0L;
                int counter = 0;
                for (var i : val) {
                    if (counter == 0 || i > max) {
                        max = i;
                        counter = 1;
                    }
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{max}));
            }
        } else if (method.equals(Reduce.length)) {
            for (var val : vall1) {
                result.add(new CopyOnWriteArrayList<>(new Double[]{(double) val.size()}));
            }
        } else if (method.equals(Reduce.min)) {
            for (var val : vall1) {
                double min = 0L;
                int counter = 0;
                for (var i : val) {
                    if (counter == 0 || i < min) {
                        min = i;
                        counter = 1;
                    }
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{min}));
            }
        } else if (method.equals(Reduce.overage)) {
            for (var val : vall1) {
                double aver = 0L;
                for (var i : val) {
                    aver += i;
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{aver / val.size()}));
            }
        } else if (method.equals(Reduce.sum)) {
            for (var val : vall1) {
                double sum = 0L;
                for (var i : val) {
                    sum += i;
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{sum}));
            }
        } else if (method.equals(Zip.concat)) {
            int len = Math.min(vall1.size(), vall2.size());
            System.out.println("Length is " + vall1.size() + " and " + vall2.size());
            for (int i = 0; i < len; ++i) {
                result.add(new CopyOnWriteArrayList<>());
                for (var val : vall1.get(i)) {
                    result.get(i).add(val);
                }
                for (var val : vall2.get(i)) {
                    result.get(i).add(val);
                }
            }
        } else if (method.equals(Product.product)) {
            int len1 = vall1.size();
            int len2 = vall2.size();
            int k = 0;
            for (int i = 0; i < len1; ++i) {
                for (int j = 0; j < len2; ++j) {
                    result.add(new CopyOnWriteArrayList<>());
                    for (var val : vall1.get(i)) {
                        result.get(k).add(val);
                    }
                    for (var val : vall2.get(j)) {
                        result.get(k).add(val);
                    }
                    ++k;
                }
            }
        }
        return (CopyOnWriteArrayList<CopyOnWriteArrayList<T1>>) (Object) result;
    }

    @Override
    protected CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> calculateThreaded(CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> val1, CopyOnWriteArrayList<CopyOnWriteArrayList<T1>> val2) {
        if (val1 == null || val2 == null || val1.isEmpty() || val2.isEmpty()) {
            return null;
        }
        if (!(val1.get(0).get(0) instanceof Integer || val1.get(0).get(0) instanceof Double || val1.get(0).get(0) instanceof Float)) {
            return null;
        }
        int y = Math.min(val1.size(), val2.size());
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> result = new CopyOnWriteArrayList<>();
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> vall1 = (CopyOnWriteArrayList<CopyOnWriteArrayList<Double>>) (Object) val1;
        CopyOnWriteArrayList<CopyOnWriteArrayList<Double>> vall2 = (CopyOnWriteArrayList<CopyOnWriteArrayList<Double>>) (Object) val2;
        y = 0;
        if (method.equals(Map.divide)) {
            ThreadPool thread = new ThreadPool(4);
            CountDownLatch cde = new CountDownLatch(vall1.size());
            for (int i = 0; i < vall1.size(); i += 100) {
                final int k = i;
                final int m = Math.min((k + 1) * 100, vall1.size());
                thread.execute(() -> {
                    for (int j = k * 100; j < m; ++j) {
                        result.add(new CopyOnWriteArrayList<>(new Double[]{(double) vall1.get(j).get(0) / vall1.get(j).get(1)}));
                    }
                    cde.countDown();
                });
            }
            try {
                cde.await();
            } catch (java.lang.InterruptedException e) {
            }
            thread.shutdown();
        } else if (method.equals(Map.add)) {
            ThreadPool thread = new ThreadPool(4);
            CountDownLatch cde = new CountDownLatch(vall1.size());
            for (int i = 0; i < vall1.size(); i += 100) {
                final int k = i;
                final int m = Math.min((k + 1) * 100, vall1.size());
                thread.execute(() -> {
                    for (int j = k * 100; j < m; ++j) {
                        result.add(new CopyOnWriteArrayList<>(new Double[]{(double) vall1.get(j).get(0) + vall1.get(j).get(1)}));
                    }
                    cde.countDown();
                });
            }
            try {
                cde.await();
            } catch (java.lang.InterruptedException e) {
            }
            thread.shutdown();
        } else if (method.equals(Map.multiply)) {
            ThreadPool thread = new ThreadPool(4);
            CountDownLatch cde = new CountDownLatch(vall1.size() % 100 == 0 ? vall1.size() / 100 : vall1.size() / 100 + 1);
            for (int i = 0; i < vall1.size(); i += 100) {
                final int k = i;
                final int m = Math.min((k + 1) * 100, vall1.size());
                thread.execute(() -> {
                    for (int j = k * 100; j < m; ++j) {
                        result.add(new CopyOnWriteArrayList<>(new Double[]{(double) vall1.get(j).get(0) * vall1.get(j).get(1)}));
                    }
                    cde.countDown();
                });
            }
            try {
                cde.await();
            } catch (java.lang.InterruptedException e) {
            }
            thread.shutdown();
        } else if (method.equals(Map.exponentiate)) {
            ThreadPool thread = new ThreadPool(4);
            CountDownLatch cde = new CountDownLatch(vall1.size());
            for (int i = 0; i < vall1.size(); i += 100) {
                final int k = i;
                final int m = Math.min((k + 1) * 100, vall1.size());
                thread.execute(() -> {
                    for (int j = k * 100; j < m; ++j) {
                        result.add(new CopyOnWriteArrayList<>(new Double[]{Math.pow(vall1.get(j).get(0) , vall1.get(j).get(1))}));
                    }
                    cde.countDown();
                });
            }
            try {
                cde.await();
            } catch (java.lang.InterruptedException e) {
            }
            thread.shutdown();
        } else if (method.equals(Reduce.max)) {
            for (var val : vall1) {
                double max = 0L;
                int counter = 0;
                for (var i : val) {
                    if (counter == 0 || i > max) {
                        max = i;
                        counter = 1;
                    }
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{max}));
            }

        } else if (method.equals(Reduce.length)) {
            for (var val : vall1) {
                result.add(new CopyOnWriteArrayList<>(new Double[]{(double) val.size()}));
            }
        } else if (method.equals(Reduce.min)) {
            for (var val : vall1) {
                double min = 0L;
                int counter = 0;
                for (var i : val) {
                    if (counter == 0 || i < min) {
                        min = i;
                        counter = 1;
                    }
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{min}));
            }
        } else if (method.equals(Reduce.overage)) {
            for (var val : vall1) {
                double aver = 0L;
                for (var i : val) {
                    aver += i;
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{aver / val.size()}));
            }
        } else if (method.equals(Reduce.sum)) {
            for (var val : vall1) {
                double sum = 0L;
                for (var i : val) {
                    sum += i;
                }
                result.add(new CopyOnWriteArrayList<>(new Double[]{sum}));
            }
        } else if (method.equals(Zip.concat)) {
            int len = Math.min(vall1.size(), vall2.size());
            System.out.println("Length is " + vall1.size() + " and " + vall2.size());
            for (int i = 0; i < len; ++i) {
                result.add(new CopyOnWriteArrayList<>());
                for (var val : vall1.get(i)) {
                    result.get(i).add(val);
                }
                for (var val : vall2.get(i)) {
                    result.get(i).add(val);
                }
            }
        } else if (method.equals(Product.product)) {
            int len1 = vall1.size();
            int len2 = vall2.size();
            int k = 0;
            for (int i = 0; i < len1; ++i) {
                for (int j = 0; j < len2; ++j) {
                    result.add(new CopyOnWriteArrayList<>());
                    for (var val : vall1.get(i)) {
                        result.get(k).add(val);
                    }
                    for (var val : vall2.get(j)) {
                        result.get(k).add(val);
                    }
                    ++k;
                }
            }
        }
        return (CopyOnWriteArrayList<CopyOnWriteArrayList<T1>>) (Object) result;
    }
}