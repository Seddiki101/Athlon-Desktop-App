/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import java.util.List;

public interface IIIService<T> {

    boolean insert(T t);

    boolean update(T t);

    boolean delete(T t);

    List<T> getAll();

    T getOne(int id);
}
