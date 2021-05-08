package com.js.interfaces;

/**
 * @Description: 创建IMessage接口担任桥接角色
 * @Param
 * @Author: 渡劫 dujie
 * @Date: 2021/5/8 3:34 PM
 */
public interface IMessage {
    void send(String message, String toUser);
}
