# -*- coding: utf-8 -*-

# Define here the models for your spider middleware
#
# See documentation in:
# https://docs.scrapy.org/en/latest/topics/spider-middleware.html
import time

from selenium import webdriver
from scrapy.http.response.html import HtmlResponse

class RentDownloaderMiddleware(object):
    # requests 则scrapy 框架去服务器下载资源
    # reponse 则跳过资源下载直接交给解析器方法
    def __init__(self):
        self.driver = webdriver.Chrome(executable_path=r"d:\chromedriver.exe")

    def process_request(self, request, spider):
        # Called for each request that goes through the downloader
        # middleware.
        # 模拟人类访问页面的行为, 并且单击收入专题按钮
        self.driver.get(request.url)
        # 为了防止加载页面过慢, 则等待一秒
        time.sleep(1)
        try:
            while True:
                show_more = self.driver.find_element_by_class_name("H7E3vT")
                show_more.click()
                print('-'*100)
                time.sleep(0.5)
        except:
            print("别再单击了, 已经没有了")
        # 创建一个response对象, 把页面信息封装到response对象
        source = self.driver.page_source
        HtmlResponse(url=self.driver.current_url,body=source, request=request,encoding='utf-8')


