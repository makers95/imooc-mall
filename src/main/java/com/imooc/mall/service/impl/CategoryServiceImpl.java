package com.imooc.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.imooc.mall.exception.ImoocMallException;
import com.imooc.mall.exception.ImoocMallExceptionEnum;
import com.imooc.mall.model.dao.CategoryMapper;
import com.imooc.mall.model.pojo.Category;
import com.imooc.mall.model.request.AddCategoryReq;
import com.imooc.mall.model.request.UpdateCategoryReq;
import com.imooc.mall.model.vo.CategoryVO;
import com.imooc.mall.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Param $
 * @return $
 **/
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public void add(AddCategoryReq addCategoryReq) {
        Category category = new Category();
        BeanUtils.copyProperties(addCategoryReq, category);
        Category categoryOld = categoryMapper.selectByName(addCategoryReq.getName());
        if (categoryOld != null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
        }
        int count = categoryMapper.insertSelective(category);
        if (count == 0) {
            throw new ImoocMallException(ImoocMallExceptionEnum.CREATE_FAILED);
        }

    }

    @Override
    public void update(Category updateCategory) {
        if (updateCategory.getName() != null) {
            Category categoryOld = categoryMapper.selectByName(updateCategory.getName());
            if (categoryOld != null && categoryOld.getId() != updateCategory.getId()) {
                throw new ImoocMallException(ImoocMallExceptionEnum.NAME_EXISTED);
            }
            int count = categoryMapper.updateByPrimaryKeySelective(updateCategory);
            if (count == 0) {
                throw new ImoocMallException(ImoocMallExceptionEnum.UPDATE_FAIL);

            }
        }
    }

    @Override
     public void delete(Integer id) {
        Category categoryOld = categoryMapper.selectByPrimaryKey(id);
        if (categoryOld == null) {
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAIL);
        }
        int count = categoryMapper.deleteByPrimaryKey(id);
        if(count==0){
            throw new ImoocMallException(ImoocMallExceptionEnum.DELETE_FAIL);
        }

    }
    @Override
    public PageInfo listForAdmin(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum,pageSize,"type,order_num");
        List<Category> categoryList = categoryMapper.selectList();
        PageInfo pageInfo = new PageInfo(categoryList);
        return  pageInfo;
    }

    @Override
    @Cacheable(value= "listCategoryForCustomer")
    public List<CategoryVO> listCategoryForCustomer(){
        ArrayList<CategoryVO> categoryVOList = new ArrayList<>();
        recursivelyFIndCategories(categoryVOList,0);
        return categoryVOList;

    }

    public void recursivelyFIndCategories(List<CategoryVO> categoryVOList, Integer parentId){
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
//        獲取所有子類別，並組合成一個目錄樹
        if(!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0; i < categoryList.size(); i++) {
                Category category = categoryList.get(i);
                CategoryVO categoryVO = new CategoryVO();
                BeanUtils.copyProperties(category,categoryVO);
                categoryVOList.add(categoryVO);
                recursivelyFIndCategories(categoryVO.getChildCategory(),categoryVO.getId());
            }
        }


    }


}
