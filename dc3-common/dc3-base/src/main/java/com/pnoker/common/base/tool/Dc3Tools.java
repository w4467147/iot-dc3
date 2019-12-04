/*
 * Copyright 2019 Pnoker. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pnoker.common.base.tool;

import cn.hutool.core.util.ReUtil;
import com.google.common.base.Charsets;
import com.pnoker.common.base.bean.NodeDto;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * <p>自定义工具类集合
 *
 * @author : pnoker
 * @email : pnokers@icloud.com
 */
public class Dc3Tools {
    /**
     * 将字符串进行Base64编码
     *
     * @param str
     * @return 返回字节流
     */
    public static byte[] encode(String str) {
        return Base64.getEncoder().encode(str.getBytes(Charsets.UTF_8));
    }

    /**
     * 将字节流进行Base64编码
     *
     * @param bytes
     * @return 返回字符串
     */
    public static String encodeToString(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * 必须配合encode使用，用于encode编码之后解码
     *
     * @param str 字符串
     * @return 返回字节流
     */
    public static byte[] decode(String str) {
        return Base64.getDecoder().decode(str);
    }

    /**
     * 必须配合encode使用，用于encode编码之后解码
     *
     * @param input 字节流
     * @return 返回字节流
     */
    public static byte[] decode(byte[] input) {
        return Base64.getDecoder().decode(input);
    }

    /**
     * 判断字符串是否为 手机号码格式
     *
     * @param phone
     * @return
     */
    public static boolean isPhone(String phone) {
        String regex = "^[1][3-9]+\\d{9}$";
        return ReUtil.isMatch(regex, phone);
    }

    /**
     * 判断字符串是否为 邮箱地址格式
     *
     * @param mail
     * @return
     */
    public static boolean isMail(String mail) {
        String regex = "^([a-zA-Z0-9_\\.\\-])+\\@(([a-zA-Z0-9\\-])+\\.)+([a-zA-Z0-9]{2,4})+$";
        return ReUtil.isMatch(regex, mail);
    }

    /**
     * 两层循环实现建树
     *
     * @param treeNodes 传入的树节点列表
     * @return
     */
    public <T extends NodeDto> List<T> buildByLoop(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(treeNode);
            }
            for (T it : treeNodes) {
                if (it.getParentId() == treeNode.getId()) {
                    if (treeNode.getChildren() == null) {
                        treeNode.setChildren(new ArrayList<>());
                    }
                    treeNode.add(it);
                }
            }
        }
        return trees;
    }

    /**
     * 使用递归方法建树
     *
     * @param treeNodes
     * @return
     */
    public <T extends NodeDto> List<T> buildByRecursive(List<T> treeNodes, Object root) {
        List<T> trees = new ArrayList<T>();
        for (T treeNode : treeNodes) {
            if (root.equals(treeNode.getParentId())) {
                trees.add(findChildren(treeNode, treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     *
     * @param treeNodes
     * @return
     */
    public <T extends NodeDto> T findChildren(T treeNode, List<T> treeNodes) {
        for (T it : treeNodes) {
            if (treeNode.getId() == it.getParentId()) {
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                treeNode.add(findChildren(it, treeNodes));
            }
        }
        return treeNode;
    }

}