import {useSuspenseQuery} from "@tanstack/react-query";

export default function InventoryCounter() {
    const response = useSuspenseQuery({
        queryKey: ["count"],
        queryFn: () => fetch("http://localhost:8080/count/").then(response => response.json()),
        refetchInterval: 1000
    });

    const data = response.data;
    console.log(data)

    return (

        <>

            <div className="absolute top-6 right-6 max-w-sm rounded-xl border border-gray-100 bg-white p-4 shadow-sm">

                <div className="max-w-sm rounded-xl">

                    <div className="flex items-center gap-3">
                        <span className="text-sm font-medium text-gray-500"> Tickets remaining: </span>
                        <span className="rounded-full bg-rose-50 px-3 py-1 text-xs font-bold text-rose-600"> {data} </span>
                    </div>

                </div>
            </div>

        </>


    )
}
