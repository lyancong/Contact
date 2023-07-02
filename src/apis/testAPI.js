import httpInstance  from "@/utils/http";


export function getCategory(){
    return httpInstance({
        url:'/game-banner'
    })
}
